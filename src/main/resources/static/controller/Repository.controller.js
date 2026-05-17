sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast",
    "sap/m/MessageBox",
    "sap/m/Dialog",
    "sap/m/Button",
    "sap/m/Input",
    "sap/m/VBox",
    "sap/m/Label",
    "sap/m/Text"
], function (Controller, JSONModel, MessageToast, MessageBox, Dialog, Button, Input, VBox, Label, Text) {
    "use strict";

    const API_BASE = "/api/repositories";

    return Controller.extend("cicd.controller.Repository", {

        onInit: function() {
            this.getView().setModel(new JSONModel({ items: [] }), "repositories");
            this._loadRepositories();
        },

        onAddRepository: function () {
            if (!this._addDialog) {
                this._addDialog = this.loadFragment({
                    name: "cicd.view.AddRepositoryDialog"
                });
            }
            this._addDialog.then(dialog => dialog.open());
        },

        onConfirmAdd: function () {
            const urlInput = this.byId("urlInput");
            const url = urlInput.getValue().trim();

            if (!url) {
                urlInput.setValueState("Error");
                urlInput.setValueStateText("URL is required");
                return;
            }

            urlInput.setValueState("None");

            fetch(API_BASE, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ url })
            })
                .then(response => {
                    if (!response.ok) return response.json()
                        .then(e => { throw new Error(e.error); });
                    return response.json();
                })
                .then(() => {
                    this._closeAddDialog();
                    MessageToast.show("Repository added successfully");
                    this._loadRepositories();
                })
                .catch(error => {
                    MessageBox.error("Could not add repository: " + error.message);
                });
        },

        onCancelAdd: function () {
            this._closeAddDialog();
        },

        _closeAddDialog: function() {
            this.byId("addRepositoryDialog").close();
            this.byId("urlInput").setValue("");
            this.byId("urlInput").setValueState("None");
        },

        onDeleteRepository: function (event) {
            const repository = event.getSource().getBindingContext("repositories").getObject();

            MessageBox.confirm("Are you sure you want to delete this repository?", {
                title: "Confirm Delete",
                onClose: action => {
                    if (action !== MessageBox.Action.OK) {
                        return;
                    }

                    fetch(`${API_BASE}/${repository.uuid}`, { method: "DELETE" })
                        .then(response => {
                            if (!response.ok) throw new Error("Delete failed");
                            MessageToast.show("Repository deleted");
                            this._loadRepositories();
                        })
                        .catch(error => {
                            MessageBox.error("Could not delete repository: " + error.message);
                        });
                }
            });
        },

        _loadRepositories: function() {
            fetch(API_BASE)
                .then(r => r.json())
                .then(data => {
                    this.getView().getModel("repositories").setProperty("/items", data);
                });
        }
    });
});