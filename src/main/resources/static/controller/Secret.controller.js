sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast",
    "sap/m/MessageBox"
], function (Controller, JSONModel, MessageToast, MessageBox) {
    "use strict";

    const API_BASE = "/api/secrets";

    return Controller.extend("cicd.controller.Secret", {

        onInit: function () {
            this.getView().setModel(new JSONModel({ items: [] }), "secrets");
            this._loadSecrets();
        },

        _loadSecrets: function() {
            fetch(API_BASE)
                .then(response => response.json())
                .then(data => {
                    this.getView().getModel("secrets").setProperty("/items", data);
                });
        },

        onDeleteSecret: function (event) {
            const secret = event.getSource().getBindingContext("secrets").getObject();

            MessageBox.confirm("Are you sure you want to delete this secret?", {
                title: "Confirm Delete",
                onClose: action => {
                    if (action !== MessageBox.Action.OK) {
                        return;
                    }

                    fetch(`${API_BASE}/${secret.id}`, { method: "DELETE" })
                        .then(response => {
                            if (!response.ok) throw new Error("Delete failed");
                            MessageToast.show("Secret deleted");
                            this._loadSecrets();
                        })
                        .catch(error => {
                            MessageBox.error("Could not delete secret: " + error.message);
                        });
                }
            });
        },

        onValidateSecret: function (event) {
            const secret = event.getSource().getBindingContext("secrets").getObject();

            fetch(`${API_BASE}/${secret.id}/validate`, { method: "POST" })
                .then(response => response.json())
                .then(result => {
                    if (result.valid) {
                        MessageBox.success(`"${secret.name}" is valid.`);
                    } else {
                        MessageBox.error(`"${secret.name}" is not valid`);
                    }
                })
                .catch(error => {
                    MessageBox.error("Validation failed: " + error.message);
                });
        },
    });
});