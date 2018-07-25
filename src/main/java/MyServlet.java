

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
        Long id = Long.parseLong(request.getParameter("id"));
        Item item = itemService.findById(id);
        response.getWriter().println(item.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Item item = convertJSONStringToObject(req);

        itemService.save(item);

        resp.getWriter().println(item.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,HibernateException {

        Item itemPut = convertJSONStringToObject(req);
        itemService.update(itemPut);
        resp.getWriter().println(itemPut.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sId = req.getParameter("id");
        Long id = Long.parseLong(sId);
        itemService.delete(id);
        resp.getWriter().println("Item id " + id + " was deleted successfully");
    }


    private Item convertJSONStringToObject(HttpServletRequest req) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Item item = mapper.readValue(req.getInputStream(), Item.class);

            return item;

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
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
