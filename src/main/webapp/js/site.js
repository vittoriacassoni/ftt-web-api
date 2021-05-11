document.addEventListener("DOMContentLoaded", init, false);

let animalURL = "/animal";

const labels = [
  'Poodle',
  'Lulu da pomerania',
  'Lhasa Apso',
  'Shih Tzu',
  'Maltes',
  'Samoieda',
  'Pug'
];


function init() {
    getAnimal(0);
    getDataChart();
}

function getDataChart(){
    const request = new XMLHttpRequest();
     request.open("GET", "/breed");

     request.onload = function () {
        const response = JSON.parse(this.responseText); 
      var data = {
          labels: labels,
            datasets: [{
              label: 'Quantidade',
              backgroundColor: 'rgb(138, 43, 226)',
              borderColor: 'rgb(138, 43, 255)',
              data: response,
            }]
      };
      const config = {
            type: 'bar',
            data: data,
            options: {
              responsive: true,
              plugins: {
                legend: {
                  position: 'top',
                },
                title: {
                  display: true,
                  text: 'As racas mais cadastradas'
                }
              }
            },
          };
        var myChart = new Chart(
        document.getElementById('myChart'),
        config
        );
        myChart.reset();
    }
    
  request.send();
}

function getAnimal(id) {
     const request = new XMLHttpRequest();
     request.open("GET", "/animal");

     request.onload = function () {
         var divList = "";
        const response = JSON.parse(this.responseText);
         $.each(response, function(index, item) { 
             divList += createListItem(item);
        });
       document.getElementById('list').innerHTML = divList;
    }
    
  request.send();
}

function createListItem(item){
    var div = `<div class="animal">
        <div class="info">
            <b>${item.name}</b>
            <b>${returnBreedName(item.breed)}</b>
            <b style="background: ${item.color}; color: ${item.color}">a</b>
        </div>
        <div class="action">
            <button onclick="editAnimal('${item.id}')">
                <i class="fa fa-pencil"></i>
            </button>
            <button onclick="deleteAnimal('${item.id}')">
                <i class="fa fa-trash"></i>
            </button>
        </div>
    </div>`;
    return div;
}

function returnBreedName(breed){
    switch (breed) {
        case 1:
          return "Poodle";
          break;
        case 2:
            return "Lulu da Pomerania";
            break
        case 3:
            return "Lhasa Apso";
            break;
        case 4:
            return "Shih Tzu";
            break;
        case 5:
            return "Maltes";
            break
        case 6:
            return "Samoieda";
            break;
        case 7:
            return "Pug";
            break;
        default:
            return "Nenhuma";
            break;
    }
}

function addAnimal(){
    clearAnimal();
    document.getElementById("title-modal").innerHTML = "Adicionar Animal";
    document.getElementById("modal").style.display = "block";
    document.getElementById("form").setAttribute("method", "POST");
}

function closeModal(){
    document.getElementById("modal").style.display = "none";
}

function foundAnimal(id){

      const request = new XMLHttpRequest();
     request.open("GET", `/animal?animalId=${id}`);

     request.onload = function () {
        const response = JSON.parse(this.responseText);
        console.log(response);
        document.getElementById("animal-id").value = response.id;
        document.getElementById("animal-id").readOnly = true;
        document.getElementById("animal-name").value = response.name;
        document.getElementById("animal-breed").value = response.breed;
        document.getElementById("animal-color").value = response.color;
    }
    
  request.send();
  
}
function editAnimal(id){
    clearAnimal();
    foundAnimal(id);
    document.getElementById("title-modal").innerHTML = "Editar Animal";
    document.getElementById("modal").style.display = "block";
}

function deleteAnimal(id){
    Swal.fire({
        title: 'Tem certeza?',
        text: "Nao sera possivel reverter!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sim!',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
            const request = new XMLHttpRequest();
 	      request.open("DELETE", `/animal?animalId=${id}`);
		  request.setRequestHeader("Content-type", "application/json; charset=utf-8");
 	      request.onload = function () {
 	    	  
 	      }
 	     request.onerror = function () {
 	        alert("erro ao executar a requisição");
 	      };
 	      request.send();
              
          Swal.fire(
            'Deletado!',
            '',
            'success'
          )
  
        location.reload();
        }
      })
}

function clearAnimal(){
    document.getElementById("animal-id").value = "";
    document.getElementById("animal-name").value = "";
    document.getElementById("animal-breed").value = "1";
    document.getElementById("animal-color").value = "";
}

function saveAnimal(){
    getAnimal(0);
    closeModal();
}