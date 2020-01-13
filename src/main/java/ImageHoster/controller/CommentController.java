package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComment(@PathVariable("imageId") Integer imageId,
                                @PathVariable("imageTitle") String imageTitle, @RequestParam("comment") String commentText, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggeduser");
        Image currentImage = imageService.getImage(imageId);
        Comment newComment = new Comment(); //create a newComment to set all the attributes of this model object
        newComment.setUser(currentUser);
        newComment.setImage(currentImage);
        newComment.setCreatedDate(new Date());
        newComment.setText(commentText);

        // call service to create comment
        try{
            commentService.createComment(newComment);
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            return "redirect:/images/" + currentImage.getId() + "/" + imageTitle;
        }
    }

}