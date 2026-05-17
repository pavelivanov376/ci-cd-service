sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "sap/m/MessageToast",
    "sap/m/MessageBox"
], function (Controller, JSONModel, MessageToast, MessageBox) {
    "use strict";

    const API_BASE = "/api/repositories";
    const SECRETS_API_BASE = "/api/secrets";

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
                .then(response => response.json())
                .then(data => {
                    this.getView().getModel("repositories").setProperty("/items", data);
                });
        },

        onAddSecret: function (event) {
            const repository = event.getSource().getBindingContext("repositories").getObject();
            this._currentRepositoryId = repository.uuid;

            if (!this._addSecretDialog) {
                this._addSecretDialog = this.loadFragment({
                    name: "cicd.view.AddSecretDialog"
                });
            }

            this._addSecretDialog.then(dialog => dialog.open());
        },

        onConfirmAddSecret: function () {
            const nameInput = this.byId("nameInput");
            const name = nameInput.getValue().trim();
            if (!name) {
                nameInput.setValueState("Error");
                nameInput.setValueStateText("Name is required");
                return;
            }

            const valueInput = this.byId("valueInput");
            const value = valueInput.getValue().trim();
            const type = this.byId("typeSelect").getSelectedKey();
            if (!value) {
                valueInput.setValueState("Error");
                valueInput.setValueStateText("Value is required");
                return;
            }

            nameInput.setValueState("None");
            valueInput.setValueState("None");

            fetch(SECRETS_API_BASE, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    name,
                    type,
                    value,
                    repositoryId: this._currentRepositoryId
                })
            })
                .then(response => {
                    if (!response.ok) return response.json()
                        .then(e => { throw new Error(e.error); });
                    return response.json();
                })
                .then(() => {
                    this._closeSecretDialog();
                    MessageToast.show("Secret added successfully");
                })
                .catch(error => {
                    MessageBox.error("Could not add secret: " + error.message);
                });
        },

        onCancelAddSecret: function () {
            this._closeSecretDialog();
        },

        _closeSecretDialog: function () {
            this.byId("addSecretDialog").close();
            this.byId("nameInput").setValue("").setValueState("None");
            this.byId("valueInput").setValue("").setValueState("None");
            this.byId("typeSelect").setSelectedItem(null);
        }
    });
});