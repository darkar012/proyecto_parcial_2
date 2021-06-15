class Trabajador {
  constructor(trabajador, id) {
    this.trab = trabajador;
    this.id = id;
  }

  render = () => {
    let component = document.createElement("div");
    component.className = "colPublicacion";

    let datosbasicos = document.createElement("div");
    datosbasicos.className = "datosbasicos";

    let nombre = document.createElement("p");
    nombre.className = "nombre";
    nombre.innerHTML = this.trab.nombre;

    let codigoOrd = document.createElement("div");
    codigoOrd.className = "pair";

    let codigoT = document.createElement("h4");
    codigoT.className = "title";
    codigoT.innerHTML = "Código:";

    let codigo = document.createElement("p");
    codigo.className = "content";
    codigo.innerHTML = this.trab.id;

    let telOrd = document.createElement("div");
    telOrd.className = "pair";

    let telT = document.createElement("h4");
    telT.className = "title";
    telT.innerHTML = "Tel:";

    let telefono = document.createElement("p");
    telefono.className = "content";
    telefono.innerHTML = this.trab.Tel;

    let asignar = document.createElement("button");
    asignar.className = "asignarBtn";
    asignar.innerHTML = "Asignar";

    

    datosbasicos.appendChild(nombre);
    codigoOrd.appendChild(codigoT);
    codigoOrd.appendChild(codigo);
    datosbasicos.appendChild(codigoOrd);
    telOrd.appendChild(telT);
    telOrd.appendChild(telefono);
    datosbasicos.appendChild(telOrd);

    component.appendChild(datosbasicos);
    component.appendChild(asignar);

    asignar.addEventListener("click", () => {
        database.ref("citaSolicitudes/"+ this.id).once("value", (data)=>{
            let cita = data.val();
            let citaConfirm = {
            correo: cita.correo,
            descripUbi: cita.descripUbi,
            descripcionPro: cita.descripcionPro,
            direccion: cita.direccion,
            fecha: cita.fecha,
            hora: cita.hora,
            id: cita.id,
            newid: cita.newid,
            nombre: cita.nombre,
            piso: cita.piso,
            telefono:cita.telefono,
            tipo: cita.tipo,
            trabajador: this.trab.id,
            trabajadorNombre: this.trab.nombre,
            trabajadorTel: this.trab.Tel
            }
            database.ref("citasConfirmadas/"+citaConfirm.id+"/"+cita.tipo).set(citaConfirm);
            database.ref("citaSolicitudes/"+ this.id).remove();
            let trabajador = {
                id: this.trab.id,
                nombre: this.trab.nombre,
                Tel: this.trab.Tel
                }
            database.ref("trabajadorOcupado/"+this.trab.id).set(trabajador);
            database.ref("Trabajadores/"+this.trab.id).remove();
            window.location.href = "index.html";
        });

        
    });

    return component;
  };
}
