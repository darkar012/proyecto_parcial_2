const database = firebase.database();
const auth = firebase.auth();

const adminName = document.getElementById("adminName");
const solicitudes = document.getElementById("solicitudesPendientes");

auth.onAuthStateChanged((user) => {
    if (user !== null) {
      database.ref("administradores/" + user.uid).once("value", (data) => {
        let us = data.val();
        adminName.innerHTML=us.nombre
        
      });
    } else {
      window.location.href = "login.html";
    }
  });

database.ref("citaSolicitudes").on("value",function(data){
solicitudes.innerHTML="";
data.forEach((cita) => {
    let valor = cita.val();
    let citaSoli = new Solicitud(valor);
    solicitudes.appendChild(citaSoli.render());
});
});