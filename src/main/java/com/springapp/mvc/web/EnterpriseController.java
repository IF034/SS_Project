package com.springapp.mvc.web;

import com.springapp.mvc.entity.Enterprise;
import com.springapp.mvc.service.CategoryService;
import com.springapp.mvc.service.CityService;
import com.springapp.mvc.service.EnterpriseService;
import com.springapp.mvc.utils.UploadedFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


@Controller
public class EnterpriseController {

    static final Logger logger = Logger.getLogger(EnterpriseController.class);

    public static final int BUFFERED_IMAGE_WIDTH = 150;
    public static final int BUFFERED_IMAGE_HEIGHT = 150;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CityService cityService;

    private  byte[] bFile = null;
    private  String fileName = "";

    @RequestMapping(value = "enterprises")
    public String enterprises(ModelMap modelMap) {

        modelMap.addAttribute("enterprise", new Enterprise());
        modelMap.addAttribute("uploadedFile", new UploadedFile());
        modelMap.addAttribute("categoryList", categoryService.getAll());
        modelMap.addAttribute("cityList", cityService.getAll());
        modelMap.addAttribute("action", "add");
        return "enterprises";
    }

    @RequestMapping(method = RequestMethod.POST, value = "enterprises/uploadFile")
    public void uploadPhoto(HttpServletRequest request,
                            HttpServletResponse response) throws FileUploadException,
            IOException {
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory())
                .parseRequest(request);
        for (FileItem item1 : items) {
            if (item1.isFormField()) {
                logger.warn("instance of FileItem represents a simple form field, not a file");

            } else {
                Iterator iterator = items.iterator();

                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField()) {
                        fileName = item.getName();
                        InputStream fileStream = item.getInputStream();
                        BufferedImage bi = ImageIO.read(fileStream);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bi, "jpg", baos);
                        bFile = baos.toByteArray();
                        System.out.println(bFile);
                    }

                }
            }
        }
    }

    @RequestMapping(value = "enterprises/add", method = RequestMethod.POST)
    public String addEnterprise(@ModelAttribute("enterprise") @Valid Enterprise enterprise,
                                BindingResult bindingResult, ModelMap modelMap) {
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("enterprise", enterprise);
            modelMap.addAttribute("categoryList", categoryService.getAll());
            modelMap.addAttribute("cityList", cityService.getAll());
            modelMap.addAttribute("action", "add");
            return "enterprises";
        }
        enterprise.setLogo(bFile);
        enterprise.setLogoName(fileName);
        bFile = null;
        fileName = null;
        enterprise.setSummaryRatio(0.0d);
        enterpriseService.add(enterprise);

        return "redirect:/";
    }

    @RequestMapping(value = "enterprises/delete/{firmId}", method = RequestMethod.GET)
    public String deleteEnterprise(@PathVariable("firmId") int firmId) {
        Enterprise enterprise = enterpriseService.get(firmId);
        enterpriseService.delete(firmId);
        logger.info("Enterprise with id= " + firmId+" deleted successfully ");
        return "redirect:/";
    }

    @RequestMapping(value = "enterprises/edit/{firmId}", method = RequestMethod.GET)
    public String getEditingEnterpriseId(@PathVariable("firmId") int firmId, ModelMap modelMap) {
        Enterprise enterpriseToEdit = enterpriseService.get(firmId);
        modelMap.addAttribute("enterprise", enterpriseToEdit);
        modelMap.addAttribute("categoryList", categoryService.getAll());
        modelMap.addAttribute("cityList", cityService.getAll());
        modelMap.addAttribute("action", "edit");
        return "enterprises";
    }

    @RequestMapping(value = "enterprises/edit", method = RequestMethod.POST)
    public String editEnterprise(@ModelAttribute("enterprise") Enterprise enterprise) {
        enterpriseService.update(enterprise);
        return "redirect:/";
    }
}


