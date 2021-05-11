package ec.ftt.api;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.ftt.dao.AnimalDao;
import ec.ftt.model.Animal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/animal")
public class AnimalApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnimalApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String animalId = request.getParameter("animalId");
             
	    if(animalId != null) {
	    	long id = Long.valueOf(animalId);
	    	
	    	AnimalDao animalDAO = new AnimalDao();
	    	
	        Animal animal = animalDAO.getAnimalById(id);
                System.out.println(animal);
	     	Gson gson = new Gson();
	    	response.getWriter().append(gson.toJson(animal));
	    	
	    } else {
	    	AnimalDao animalDAO = new AnimalDao();
	    	
	    	List<Animal> animals = null;
                try {
                    animals = animalDAO.getAllAnimal();
                } catch (SQLException ex) {
                    Logger.getLogger(AnimalApi.class.getName()).log(Level.SEVERE, null, ex);
                }
	        System.out.println(animals);
	    	Gson gson = new Gson();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
	    	response.getWriter().append(gson.toJson(animals));
            }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("oi");
		Animal a = new Animal(
				request.getParameter("animal-id"),
				request.getParameter("animal-name"),
				request.getParameter("animal-breed"),
				request.getParameter("animal-color")
				);
		
		AnimalDao animalDao = new AnimalDao();
                
                if(animalDao.getAnimalById(a) != null){
                    animalDao.updateAnimal(a);
                }
                else{
                    animalDao.addAnimal(a);
                }
		
		
		System.out.println(a);
		
		//response.getWriter().append(a.toString());
                response.sendRedirect("index.html");
		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Animal a = new Animal(
		request.getParameter("animal-id"),
		request.getParameter("animal-name"),
		request.getParameter("animal-breed"),
		request.getParameter("animal-color")
            );
            AnimalDao animalDAO = new AnimalDao();
		
            animalDAO.updateAnimal(a);
		
            System.out.println(a);
		
            response.sendRedirect("index.html");	
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setStatus(418); //200 - OK - Padrão (Default)

            if (request.getParameter("animalId") == null)
                response.sendError(407, "Informe o ID do usuário a ser retornado!!!" );
            else {
                Long animalId = Long.valueOf(request.getParameter("animalId"));

                AnimalDao animalDAO = new AnimalDao();
		
                animalDAO.deleteAnimal(animalId);
		
                response.getWriter().append(request.getParameter("animalId") + " User removido");
            }
	}

}
