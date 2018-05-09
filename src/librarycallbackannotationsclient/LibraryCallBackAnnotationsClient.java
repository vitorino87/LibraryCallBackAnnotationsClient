/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarycallbackannotationsclient;

import com.tutorialspoint.entity.Book;
import com.tutorialspoint.stateful.LibraryStatefulSessionBeanRemote;
import com.tutorialspoint.stateless.LibraryStatelessCallbackAnnotationsRemote;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author tiago.lucas
 */
public class LibraryCallBackAnnotationsClient {

    BufferedReader brConsoleReader = null;
    Properties props;
    InitialContext ctx;
    {
        props = new Properties();
        try{
            props.load(new FileInputStream("jndi.properties"));
        }catch(IOException ex){
            ex.printStackTrace();
        }
        try{
            ctx = new InitialContext(props);            
        }catch(NamingException ex){
            ex.printStackTrace();
        }
        brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LibraryCallBackAnnotationsClient ejbTester = new LibraryCallBackAnnotationsClient();
        ejbTester.testEntityEjb();
    }
    
    private void showGUI(){
        System.out.println("**************************");
        System.out.println("Welcome to Book Store");
        System.out.println("**************************");
        System.out.print("Option\n1. Add Book\n2. Exit\nEnter Choice: ");
    }

    private void testEntityEjb() {
        try{
            int choice = 1;
            /*LibraryStatelessCallbackAnnotationsRemote libraryBean =
                    (LibraryStatelessCallbackAnnotationsRemote)ctx
                            .lookup("LibraryStatelessCallbackAnnotations/remote");*/
            LibraryStatefulSessionBeanRemote libraryBean =
                    (LibraryStatefulSessionBeanRemote)ctx
                            .lookup("LibraryStatefulSessionBean/remote");
            
            while(choice !=2){
                String bookName;
                showGUI();
                String strChoice = brConsoleReader.readLine();
                choice = Integer.parseInt(strChoice);
                if(choice == 1){
                    System.out.print("Enter book name: ");
                    bookName = brConsoleReader.readLine();
                    Book book = new Book();
                    book.setName(bookName);
                    //libraryBean.addBooks(book);
                    libraryBean.addBook(bookName);
                }else if(choice == 2)
                    break;
            }
            
            //List<Book> booksList = libraryBean.getBooks();
            List<String> booksList = libraryBean.getBooks();
            System.out.println("Book(s) entered so far: "+booksList.size());
            int i=0;
            /*for(Book book:booksList){
                System.out.println((i+1)+". "+book.getName());
                i++;
            }*/
            for(i=0;i<booksList.size();i++){
                System.out.println((i+1)+". "+booksList.get(i));
                i++;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
            try{
                if(brConsoleReader != null){
                    brConsoleReader.close();
                }
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
