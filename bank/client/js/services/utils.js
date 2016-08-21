(function(window, document, undefined) {
  bankApp.service('utils', [function() {
    this.handleServerError = function(response) {
      console.log(response);
      var title = response.status + " " + response.statusText + ": ";
      var message = '';
      if(response.data.error){
        title = title + response.data.error.title
        message = response.data.error.message
      };
      var rld = confirm(title + "\n" + message + "\n" + "Refresh?");
      if (rld)
        location.reload(true);
    };
  }]);})(window, document);