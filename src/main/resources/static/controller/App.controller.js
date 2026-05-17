sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel"
], function (Controller, JSONModel) {
    "use strict";

    return Controller.extend("cicd.controller.App", {

        onInit: function () {
            var viewModel = new JSONModel({
                selectedTab: "repositories"
            });
            this.getView().setModel(viewModel);

            var router = this.getOwnerComponent().getRouter();
            router.attachRouteMatched(this.onRouteMatched, this);
        },

        onRouteMatched: function (event) {
            var routeName = event.getParameter("name");
            var viewModel = this.getView().getModel();

            if (routeName === "repositories") {
                viewModel.setProperty("/selectedTab", "repositories");
            } else if (routeName === "secrets") {
                viewModel.setProperty("/selectedTab", "secrets");
            }
        },

        onTabSelect: function (event) {
            var key = event.getParameter("key");
            var router = this.getOwnerComponent().getRouter();

            if (key === "repositories") {
                router.navTo("repositories");
            } else if (key === "secrets") {
                router.navTo("secrets");
            }
        }

    });
});