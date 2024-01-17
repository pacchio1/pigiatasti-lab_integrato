const DATI = {};
const URLREG = "http://localhost:8080/api/v1/auth/register";
const URLID = "http://localhost:8080/users/getid/";
const URLIMG = "http://localhost:8080/images/"

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $('#imagePreview').css('background-image', 'url('+e.target.result +')');
            $('#imagePreview').hide();
            $('#imagePreview').fadeIn(650);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#imageUpload").change(function() {
    readURL(this);
});

//la funzione deve essere asincrona così puoi usare await
async function primiDati(){
    //event.preventDefault() è utile nel caso in cui devi cambiare pagina dopo aver preso i dati, 
    //di default dopo che clicchi un pulsante la pagina si aggiorna, con questo gli dici di non farlo
    event.preventDefault();
    if (document.getElementById("genere").value==0)
        swal({
            title: "Attenzione!",
            text: "Seleziona un genere",
            icon: "warning",
            button: "OK"
        })
    else{
        DATI.nome = document.getElementById("nome").value;
        DATI.cognome = document.getElementById("cognome").value;
        DATI.email = document.getElementById("email").value;
        DATI.telefono = document.getElementById("telefono").value;
        DATI.password = document.getElementById("password").value;
        DATI.id_gender = document.getElementById("genere").value;
        DATI.presentazione = document.getElementById("descrizione").value;
        console.log(DATI);
        //ti fai salvare il risultato della fetch in una const e await dice di non proseguire nel codice fino a che non ha preso i dati
        const response = await fetch(URLREG,{
            method:"POST",
            headers: {
            'Content-Type': 'application/json'
            },
            body: JSON.stringify(DATI)
        })
        //nel dubbio metti await anche nella conversione col json
        const data = await response.json()
        localStorage.setItem('token', data.token);
        //stessa cosa per la seconda fetch
        const risp = await fetch(`${URLID}${DATI.email}`,{
            method: 'POST'
          });
        const vals = await risp.json()
        localStorage.setItem('id_utente', vals.id_utente);
        
        
        const formData = new FormData();
        formData.append('image', document.getElementById('imageUpload').files[0]);
        formData.append('id_utente', vals.id_utente);

        const uploadResponse = await fetch(`${URLIMG}upload`, {
        method: 'POST',
        body: formData,
        });

        console.log(uploadResponse)


    }
}

