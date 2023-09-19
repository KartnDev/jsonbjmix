package com.company.jsonbtest.view.user;

import com.company.jsonbtest.entity.User;
import com.company.jsonbtest.view.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;

import java.util.Optional;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {


    @ViewComponent
    private DataGrid<User> usersDataGrid;

    @Subscribe
    public void onInit(final InitEvent event) {
        usersDataGrid.addColumn(new ComponentRenderer<>(e -> new Text((String)
                Optional.ofNullable(e.getLocation())
                        .map(v -> v.getContainer().get("street"))
                        .orElse(""))))
                        .setHeader("street");
        usersDataGrid.addColumn(new ComponentRenderer<>(e -> new Text((String)
                Optional.ofNullable(e.getLocation())
                        .map(v -> v.getContainer().get("address"))
                        .orElse(""))))
                .setHeader("street");
    }


}