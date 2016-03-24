package cpslabteam.bank.webservice.resources;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.utils.SessionManager;
import cpslabteam.bank.jsonserialization.JSONViews;

public class CustomersResource extends ServerResource {
	
	@Get("application/json")
	public String doGet(Representation entity) throws InterruptedException, IOException, JSONException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			List<Customer> customers = customerDAO.findAll();
			ObjectWriter objectWriter = new ObjectMapper().writerWithView(JSONViews.Info.class);
			String response = objectWriter.writeValueAsString(customers);
			SessionManager.getSession().getTransaction().commit();
			return response;
		} catch (HibernateException e) {
			SessionManager.getSession().getTransaction().rollback();
			throw e;
		} catch (Exception e){
			e.printStackTrace();
			return new JSONObject().put("error", e.getMessage()).toString();
		}
	}

}