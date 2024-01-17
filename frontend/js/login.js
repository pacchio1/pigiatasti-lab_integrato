async function provaLogin(){
  event.preventDefault();
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;

  console.log(email, password);

  try {
    const response = await fetch('http://localhost:8080/api/v1/auth/authenticate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, password })
    });
    const data = await response.json();
    console.log("ciao"+data.token);
    if (data.token) {
      // L'autenticazione ha successo, memorizza il token JWT
      localStorage.setItem('token', data.token);

      const risposta = await fetch(`http://localhost:8080/users/getid/${email}`,{
        method: 'POST'
      });
      const id_utente = await risposta.json();
      localStorage.setItem('id_utente', id_utente.id_utente);
      console.log(id_utente);
      window.location.href = 'http://127.0.0.1:5500/frontend/index.html';
    } else {
      // L'autenticazione ha fallito, gestisci l'errore
      console.error('Autenticazione fallita');
      swal({
        title: 'Autenticazione fallita',
        icon: 'error',
        button: 'OK',
      })
    }
    
  } catch (error) {
    console.error('Si è verificato un errore:', error);
  }
}
/*
function provaLogin(){
  event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    console.log(email, password);

    fetch('http://localhost:8080/api/v1/auth/authenticate', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ email, password })
  })
  .then(response => response.json())
  .then(data => {
    console.log("ciao"+data.token);
    if (data.token) {
      // L'autenticazione ha successo, memorizza il token JWT
      localStorage.setItem('token', data.token);
  
      fetch(`http://localhost:8080/users/getid/${email}`,{
        method: 'POST'
      })
      .then(risposta => risposta.json())
      .then(risposta => {
          localStorage.setItem('id_utente', risposta.id_utente);
          console.log(risposta);
      });
      
    } else {
      // L'autenticazione ha fallito, gestisci l'errore
      console.error('Autenticazione fallita');
    }
  })
  .then(() => {
    //window.location.href = 'http://127.0.0.1:5500/frontend/index.html';
  })
  .catch(error => {
    console.error('Si è verificato un errore:', error);
  });
}
*/