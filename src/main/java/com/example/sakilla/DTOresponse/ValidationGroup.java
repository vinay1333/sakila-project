package com.example.sakilla.DTOresponse;

import jakarta.validation.groups.Default;

public class ValidationGroup {

    public interface Create extends Default {}
    public interface Update extends Default {}
    public interface Push extends Default {}
    public interface Delete extends Default{}

}
