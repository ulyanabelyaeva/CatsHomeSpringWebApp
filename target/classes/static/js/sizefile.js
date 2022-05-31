function validateFileExtension(fld) {
            if(fld.files[0].size > 1000000) {
            alert("Размер картинки слишком большой");
            fld.form.reset();
            fld.focus(); 
            return false;
        }
        return true;
        }