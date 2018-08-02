

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = "/test")
public class MyServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Item item = itemService.findById(id);
            if (item == null) {
                response.getWriter().println("Item with id " + id + " doesn't exist in DB");
            } else {
                response.getWriter().println(item.toString());
            }
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            response.getWriter().println("Getting unsuccessful " + e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item item = convertJSONStringToObject(req);
        try {
            itemService.save(item);
            resp.getWriter().println(item.toString());
        } catch (BadRequestException | HibernateException | IOException e) {
            e.printStackTrace();
            resp.getWriter().println("Saving unsuccessful " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Item itemPut = convertJSONStringToObject(req);
        try {
            itemService.update(itemPut);
            resp.getWriter().println(itemPut.toString());
        } catch (BadRequestException | HibernateException | IOException e) {
            e.printStackTrace();
            resp.getWriter().println("Updating unsuccessful " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sId = req.getParameter("id");
        Long id = Long.parseLong(sId);

        try {
            itemService.delete(id);
            resp.getWriter().println("Item id " + id + " was deleted successfully");
        } catch (BadRequestException | HibernateException | IOException e) {
            e.printStackTrace();
            resp.getWriter().println("Deleting unsuccessful " + e.getMessage());
        }
    }


    private Item convertJSONStringToObject(HttpServletRequest req) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = req.getInputStream()) {

            Item item = mapper.readValue(is, Item.class);

            return item;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //servlet registration - init()
    //servlet work with servlet methods
    //to close servlet with its resources - destroy();

    //HTTP Requests
    //GET - get some info
    //POST - save some info
    //PUT - update
    //DELETE - delete info

    //CRUD


}
