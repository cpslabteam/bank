package cpslabteam.bank.webservice.resources;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import cpslabteam.bank.database.dao.CustomerDAO;
import cpslabteam.bank.database.dao.DAOFactory;
import cpslabteam.bank.database.objects.Customer;
import cpslabteam.bank.database.utils.SessionManager;
import cpslabteam.bank.jsonserialization.BankJsonSerializer;

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
			Customer customer = customerDAO.findById(Long.valueOf(customerID));
			String response = BankJsonSerializer.serializeDetails(customer);
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
