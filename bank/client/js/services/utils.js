(function(window, document, undefined) {
  bankApp.service('utils', [function() {
    this.handleServerError = function(response) {
      console.log(response);
      var title = response.status + " " + response.statusText + ": " +
        response.data
        .error.title;
      var message = response.data.error.message;
      var rld = confirm(title + "\n" + message + "\n" + "Refresh?");
      if (rld)
        location.reload(true);
    };
  }]);})(window, document);