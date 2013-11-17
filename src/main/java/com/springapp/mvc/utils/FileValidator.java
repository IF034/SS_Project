package com.springapp.mvc.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return false;
    }

    @Override
    public void validate(Object uploadedFile, Errors errors) {

        UploadedFile file = (UploadedFile) uploadedFile;

        if (file.getFile().getSize() == 0) {
            errors.rejectValue("file", "uploadForm.salectFile",
                    "Your file must not be null size");
        }

        if (!(file.getFile().getContentType().equals("image/jpeg"))) {
            errors.rejectValue("file", "uploadForm.salectFile",
                    "Please select an image");
        }

    }

}
