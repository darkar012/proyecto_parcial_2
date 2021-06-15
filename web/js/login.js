const database = firebase.database();
const auth = firebase.auth();

const correo = document.getElementById("email");
const password = document.getElementById("password");
const loginBtn = document.getElementById("signIn");

auth.onAuthStateChanged((user) => {
  if (user !== null) {
    window.location.href = "index.html";
  }
});

loginBtn.addEventListener("click", () => {
  auth
    .signInWithEmailAndPassword(correo.value, password.value)
    .then((data) => {
      window.location.href = "index.html";
      correo.value="";
      password.value="";
    })
    .catch((error) => {
      alert("Esta cuenta no existe, registrese");
    });
});
