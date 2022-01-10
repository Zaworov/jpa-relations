import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addorder")
public class AddOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @PersistenceUnit(name = "myPersistenceUnit")
    private EntityManagerFactory emf;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();

        //creating order
        Order order = new Order();
        order.setProduct(request.getParameter("productName"));
        order.setOrderDetails(request.getParameter("orderDetails"));

        //connecting order with client
        long clientId = Long.parseLong(request.getParameter("clientId"));
        Client client = em.find(Client.class, clientId);
        order.setClient(client);

        //persisting order in db
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(order);
        tx.commit();
        em.close();

        response.sendRedirect(request.getContextPath());
    }
}