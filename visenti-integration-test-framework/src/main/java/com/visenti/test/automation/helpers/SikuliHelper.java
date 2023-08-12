package com.visenti.test.automation.helpers;

import com.visenti.test.automation.utils.CommonUtils;
import org.sikuli.basics.Debug;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class SikuliHelper {

    public void clickOnTheImage(String imageFile) throws FindFailed{
        String userDir = System.getProperty("user.dir");

        Pattern image = new Pattern(userDir + "\\src\\main\\resources\\images\\" + imageFile + ".png");
        image.similar(0.6);

        Screen screen = new Screen();

        Match target = screen.exists(image);
        if(null == target){
            Debug.error("Not found: ", image);
        }else{
            Debug.info("Found: ", image);
            screen.hover(image);
            screen.click(image);
        }

        CommonUtils.wait(2);
    }

}
