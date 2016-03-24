package cpslabteam.bank.webservice.resources;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.utils.SessionManager;
import cpslabteam.bank.jsonserialization.JSONViews;

public class CustomerResource extends ServerResource {

	private String customerID;

	@Override
	protected void doInit() throws ResourceException {
		customerID = getAttribute("customer");
	}

	@Get("application/json")
	public String doGet() throws InterruptedException, IOException, JSONException {
		try {
			SessionManager.getSession().beginTransaction();
			DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			Customer customer = customerDAO.findById(new Long(customerID));
			ObjectWriter objectWriter = new ObjectMapper()
					.registerModule(
							new Hibernate5Module(SessionManager.getSessionFactory()).enable(Feature.FORCE_LAZY_LOADING))
					.writerWithView(JSONViews.Details.class);
			String response = objectWriter.writeValueAsString(customer);
			SessionManager.getSession().getTransaction().commit();
			return response;
		} catch (HibernateException e) {
			SessionManager.getSession().getTransaction().rollback();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject().put("error", e.getMessage()).toString();
		}
	}
}
