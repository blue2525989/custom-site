var results = document.getElementById('results');

results.innerHTML = '<h1></h1>';

/* gets the time and adds 7 hours */
var d = new Date();                                                             /* 29 is magic number */
d.setTime(d.getTime() + d.getTimezoneOffset() * 60 * 1000 /* convert to UTC */ - (/* UTC-29 */ 29) * 60 * 60 * 1000);
document.getElementById("date").innerHTML = d;