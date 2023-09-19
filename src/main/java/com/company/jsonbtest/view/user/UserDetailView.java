package com.company.jsonbtest.view.user;

import com.company.jsonbtest.entity.User;
import com.company.jsonbtest.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import io.jmix.core.EntityStates;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Route(value = "users/:id", layout = MainView.class)
@ViewController("User.detail")
@ViewDescriptor("user-detail-view.xml")
@EditedEntityContainer("userDc")
public class UserDetailView extends StandardDetailView<User> {

    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private PasswordField passwordField;
    @ViewComponent
    private PasswordField confirmPasswordField;
    @ViewComponent
    private ComboBox<String> timeZoneField;

    @Autowired
    private EntityStates entityStates;
    @Autowired
    private MessageBundle messageBundle;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @ViewComponent
    private TypedTextField locationAddr;
    @ViewComponent
    private TypedTextField locationStreet;

    @Subscribe
    public void onInit(final InitEvent event) {
        timeZoneField.setItems(List.of(TimeZone.getAvailableIDs()));
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<User> event) {
        usernameField.setReadOnly(false);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
        if(getEditedEntity().getLocation() != null && getEditedEntity().getLocation().getContainer().containsKey("address")) {
            locationAddr.setValue((String) getEditedEntity().getLocation().getContainer().get("address"));
        }
        if(getEditedEntity().getLocation() != null && getEditedEntity().getLocation().getContainer().containsKey("street")) {
            locationAddr.setValue((String) getEditedEntity().getLocation().getContainer().get("street"));
        }
    }

    @Subscribe("locationStreet")
    public void onLocationStreetComponentValueChange(final AbstractField.ComponentValueChangeEvent<TypedTextField, String> event) {
        getEditedEntity().getLocation().getContainer().put("street", event.getValue());
    }

    @Subscribe("locationAddr")
    public void onLocationAddrComponentValueChange(final AbstractField.ComponentValueChangeEvent<TypedTextField, String> event) {
        getEditedEntity().getLocation().getContainer().put("address", event.getValue());
    }

    @Subscribe
    public void onValidation(final ValidationEvent event) {
        if (entityStates.isNew(getEditedEntity())
                && !Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
            event.getErrors().add(messageBundle.getMessage("passwordsDoNotMatch"));
        }
    }

    @Subscribe
    protected void onBeforeSave(final BeforeSaveEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }
}