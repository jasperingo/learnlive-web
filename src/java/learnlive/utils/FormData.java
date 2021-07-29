
package learnlive.utils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;


public class FormData {
    
    private String formError;
    
    private String formSuccess;
    
    private Map<String, String[]> data = new HashMap<>();
    
    private Map<String, String[]> errors = new HashMap<>();
    
    
    public String getFormError() {
        return formError;
    }
    
    public void setFormError(String formError) {
        this.formError = formError;
    }
    
    public String getFormSuccess() {
        return formSuccess;
    }
    
    public void setFormSuccess(String formSuccess) {
        this.formSuccess = formSuccess;
    }
    
    public Map<String, String[]> getData() {
        return data;
    }
    
    public void setData(Map<String, String[]> data) {
        this.data = data;
    }

    public Map<String, String[]> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String[]> errors) {
        this.errors = errors;
    }
    
    public void putError(String key, String error) {
       putError(key, new String[] {error});
    }
    
    public void putError(String key, String error[]) {
        errors.put(key, error);
    }
    
    public void putData(String key, String value) {
       putData(key, new String[] {value});
    }
    
    public void putData(String key, String[] value) {
        data.put(key, value);
    }
    
    public boolean hasData() {
        return !data.isEmpty();
    }
    
    public boolean hasErrors() {
        return formError != null || !errors.isEmpty();
    }
    
    public boolean hasFormError() {
        return formError != null;
    }
    
    public boolean hasDataErrors() {
        return !errors.isEmpty();
    }
    
    public boolean hasError(String key) {
        return errors.containsKey(key);
    }
    
    public boolean hasSuccess() {
        return formSuccess != null;
    }
    
    
    public static class Flasher {
        
        private FormData formData;

        public Flasher(HttpSession session) {
            this(session, "form_data");
        }

        public Flasher(HttpSession session, String key) {
            formData = (FormData) session.getAttribute(key);
            session.removeAttribute(key);
        }

        public boolean hasError() {
            return formData != null && formData.hasErrors();
        }

        public boolean hasFormError() {
            return formData != null && formData.hasFormError();
        }

        public boolean hasDataError() {
            return formData != null && formData.hasDataErrors();
        }

        public boolean hasSuccess() {
            return formData != null && formData.hasSuccess();
        }

        public boolean hasData() {
            return formData != null && formData.hasData();
        }

        public boolean hasError(String key) {
            String err = getError(key);
            return err != null && !err.isEmpty();
        }

        public String getFormError() {
            return formData != null ? (formData.getFormError() == null ? "" : formData.getFormError()) : "";
        }

        public String getFormSuccess() {
            return formData != null ? formData.getFormSuccess() : "";
        }

        public String getError(String key) {
            String[] strings = getErrorArray(key);
            return strings == null ? "" : strings[0];
        }

        public String[] getErrorArray(String key) {
            return formData != null ? formData.getErrors().getOrDefault(key, null) : null;
        }

        public String getData(String key) {
            String[] strings = getDataArray(key);
            return strings == null ? "" : strings[0];
        }
        
        public String[] getDataArray(String key) {
            return formData != null ? (formData.getData().get(key) == null ? null : formData.getData().get(key)) : null;
        }
    
    }
    
}




