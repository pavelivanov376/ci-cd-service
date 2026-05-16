sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel"
], function (Controller, JSONModel) {
    "use strict";

    return Controller.extend("cicd.controller.Repository", {

        onInit: function() {
            this.getView().setModel(new JSONModel({ items: [] }), "repositories");
            this._loadRepositories();
        },

        _loadRepositories: function() {
            fetch("/api/repositories")
                .then(r => r.json())
                .then(data => {
                    this.getView().getModel("repositories").setProperty("/items", data);
                });
        }
    });
});