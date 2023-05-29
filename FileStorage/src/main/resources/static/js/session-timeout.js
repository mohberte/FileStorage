function checkSessionTimeout() {
	 var cookieValue = "; " + document.cookie;
  var parts = cookieValue.split("; sessionTimeoutTimestamp=");
  var time=0;
  if (parts.length == 2) {
    time= parseInt(parts.pop().split(";").shift());
  }
  
  if (time > 0) {
   var secondsBeforeExpire = time;

    var timeToDecide = secondsBeforeExpire*1000;
    document.getElementById("time").textContent=timeToDecide/1000; //display the timer in the page
    var logoutUrl = '/customLogout'; // URL to logout page.

    // Log user out if timer hits 0
    setInterval(function () {
        timeToDecide=timeToDecide-1000;
        document.getElementById("time").textContent =timeToDecide/1000;
        if(timeToDecide==0)
            window.location = logoutUrl;
    }, 1000);

    // Reset timer
    function continueTimer() {
        timeToDecide = secondsBeforeExpire*1000; ;
    }
    }
}

// Use an event listener to call the checkSessionTimeout function when the page loads
document.addEventListener('DOMContentLoaded', function() {
    checkSessionTimeout();
});