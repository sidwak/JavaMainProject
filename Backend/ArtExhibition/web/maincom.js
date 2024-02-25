function onbodyload(){
  checkCredentials();
};
function checkCredentials() {
  var storedUsername = sessionStorage.getItem('email');
  var storedPassword = sessionStorage.getItem('password');

  if (storedUsername && storedPassword) {
      $("#notlog").hide();
      $("#actlog").show();
  } else {
      $("#notlog").show();
      $("#actlog").hide();
  }
}
function onlogout(){
  var formData = {
      type: "logout"
  };

  $.ajax({
      type: 'POST',
      url: 'LoginServlet', 
      data: formData,
      success: function(response) {
          
      },
      error: function() {
          alert('Error occurred while processing your request.');
      }
  });
  sessionStorage.removeItem('email');
  sessionStorage.removeItem('password');
  location.reload();
}