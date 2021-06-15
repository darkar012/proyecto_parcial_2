class Solicitud {
  constructor(cita) {
    this.cita = cita;
  }

  render = () => {
    let component = document.createElement("div");
    component.className = "colPublicacion";

    let datosbasicos = document.createElement("div");
    datosbasicos.className = "datosbasicos";

    let fecha = document.createElement("p");
    fecha.className = "fechaCita";
    fecha.innerHTML = this.cita.fecha;

    let horaorden = document.createElement("div");
    horaorden.className = "horaorden";

    let horaT = document.createElement("h4");
    horaT.className = "horaT";
    horaT.innerHTML = "hora: ";

    let hora = document.createElement("p");
    hora.className = "Hora";
    hora.innerHTML = this.cita.hora;

    let lugarorden = document.createElement("div");
    lugarorden.className = "lugarorden";

    let lugarT = document.createElement("h4");
    lugarT.className = "lugarT";
    lugarT.innerHTML = "Dirección: ";

    let lugar = document.createElement("p");
    lugar.className = "lugar";
    lugar.innerHTML = this.cita.direccion;

    let tipo = document.createElement("p");
    tipo.className = "tipo";
    tipo.innerHTML = this.cita.tipo;

    let descripcionOrd = document.createElement("div");
    descripcionOrd.className = "descripcionOrd";

    let descripciont = document.createElement("h4");
    descripciont.className = "descripciont";
    descripciont.innerHTML = "Descripción del Problema";

    let descripcion = document.createElement("p");
    descripcion.className = "descripcion";
    descripcion.innerHTML = this.cita.descripcionPro;

    let asignar = document.createElement("button");
    asignar.className = "asignarBtn";
    asignar.innerHTML = "Asignar Cita";

    

    datosbasicos.appendChild(fecha);
    horaorden.appendChild(horaT);
    horaorden.appendChild(hora);
    datosbasicos.appendChild(horaorden);
    lugarorden.appendChild(lugarT);
    lugarorden.appendChild(lugar);
    datosbasicos.appendChild(lugarorden);
    datosbasicos.appendChild(tipo);

    component.appendChild(datosbasicos);

    descripcionOrd.appendChild(descripciont);
    descripcionOrd.appendChild(descripcion);
    component.appendChild(descripcionOrd);
    component.appendChild(asignar);

    asignar.addEventListener("click", () => {
        let ref = database.ref("tempKey/"+ this.cita.newid);
        let nuevaid = {
            id: this.cita.newid
        }
        ref.set(nuevaid);
        window.location.href = "asignarCitas.html";
    });

    
    
    return component;
  };
}
