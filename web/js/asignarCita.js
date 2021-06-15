const database = firebase.database();
const auth = firebase.auth();

let id;

const adminName = document.getElementById("adminName");
const nombre = document.getElementById("nombre");
const telefono = document.getElementById("numero");
const correo = document.getElementById("correo");
const tipo = document.getElementById("tipo");
const descrip = document.getElementById("descripcion");
const direccion = document.getElementById("direccion");
const piso = document.getElementById("piso");
const descripUbi = document.getElementById("descripcionUbi");
const fecha = document.getElementById("fecha");
const hora = document.getElementById("hora");

const tecnicos = document.getElementById("tecnicos");

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

  database.ref("tempKey").on("value",function(data){
    data.forEach((cita) => {
let c = cita.val();
id = c.id;
database.ref("tempKey/"+id).remove();
console.log(id);
database.ref("citaSolicitudes/"+ id).once("value",(data)=>{
let cita = data.val();
nombre.innerHTML=cita.nombre;
correo.innerHTML=cita.correo;
telefono.innerHTML=cita.telefono;
tipo.innerHTML=cita.tipo;
descrip.innerHTML=cita.descripcionPro;
direccion.innerHTML=cita.direccion;
piso.innerHTML=cita.piso;
descripUbi.innerHTML=cita.descripUbi;
fecha.innerHTML=cita.fecha;
hora.innerHTML=cita.hora;
});
    });
  });

  database.ref("Trabajadores").on("value",function(data){
    tecnicos.innerHTML="";
    data.forEach((cita) => {
        let valor = cita.val();
        let citaSoli = new Trabajador(valor,id);
        tecnicos.appendChild(citaSoli.render());
    });
  });

  