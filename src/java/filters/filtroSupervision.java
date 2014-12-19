

package filters;


import entities.Usuario;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebFilter(filterName = "filtroSupervision",urlPatterns = {"/supervision/*"})
public class filtroSupervision implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (session.getAttribute("admin")==null)
           response.sendRedirect(request.getContextPath() + "/zonaRestringida.xhtml");  
            
        else if(session.getAttribute("admin")!=null){
            
            Usuario u=(Usuario)session.getAttribute("admin");
            if(u.getTipoUsuario()==2){
               response.sendRedirect(request.getContextPath() + "/admin/index.xhtml"); 
            }
             else{
            
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.
           
            
           
            
             chain.doFilter(req, res);
             
        }
        }    
            
        
            
                
                
       

    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
}
