function img_categoria(categoria){
    switch(categoria){
        case "Film":
            return "icona_film.jpg";
        case "GiochiInScatola":
            return "giochi_scatola.jpg";
        case "Cocktail":
            return "icona_cocktail.jpg";
        case "Videogiochi":
            return "icona_videogiochi.jpg";
        case "Ristorante":
            return "icona_ristorante.jpg";
        case "Sport-AttivitaFisica":
            return "icona_sport.jpg";
        case "Altro":
            return "icona_altro.jpg";
    }
}

function img_categoria_num(num){
  switch(num){
          case 1:
              return "icona_film.jpg";
          case 2:
              return "giochi_scatola.jpg";
          case 3:
              return "icona_cocktail.jpg";
          case 4:
              return "icona_videogiochi.jpg";
          case 5:
              return "icona_ristorante.jpg";
          case 6:
              return "icona_sport.jpg";
          case 7:
              return "icona_altro.jpg";
      }
}
//2023-06-30T15:54:00.000+00:00

function gestioneData(data){
  let giorno = data.split("T")[0];
  let orario = data.split("T")[1];
  orario = orario.split("+")[0].split(":");
  giorno = giorno.split("-");
  return `${giorno[2]}-${giorno[1]}-${giorno[0]} ${orario[0]}:${orario[1]}`;
}

function gestionePosizione(posizione) {
  const pos = [];
  let indirizzo = JSON.parse(posizione).indirizzo.split(",");
  pos.push(`${indirizzo[0]}, ${indirizzo[1]}`)
  pos.push(`${indirizzo[2]}, ${indirizzo[3]}, ${indirizzo[4]}`)
  pos.push(JSON.parse(posizione).lat)
  pos.push(JSON.parse(posizione).lon)
  
  return pos;
  //return posizione;
}

function avvio(){
    pulsanteNav()
    AggiornaMappa()
}

function pulsanteNav(){
    const LI = document.getElementById("accedi-areapers")
    const ID = localStorage.getItem("token")
    if (ID!=null){
        console.log(ID);
        LI.innerHTML = "<a href='schermata_utente_pers.html'>Area Personale</a>"
    }else{
        LI.innerHTML = "<a href='Login.html'>Accedi</a>"
    }
}
function AggiornaMappa() {
    var lat_in=45.070312;
    var long_in=7.6868565;
    // Create a map instance
    var map = L.map('map').setView([lat_in,long_in], 13);
  
    // Add the OpenStreetMap tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
      maxZoom: 18,
    }).addTo(map);
  
    // Add a marker to the map
    L.marker([lat_in,long_in]).addTo(map);
  
    // Add search functionality
    var searchInput = document.getElementById('textPosizione');
    // Removes the marker present on the map
    function removeAllMarkers() {
    map.eachLayer(function (layer) {
      if (layer instanceof L.Marker) {
        map.removeLayer(layer);
      }
    });
  }
    // Function to handle the search
    function performSearch() {
      var searchText = searchInput.value;
      // Use your OpenStreetMap API key in the request URL
      var url = 'https://nominatim.openstreetmap.org/search?format=json&q=' + searchText + '&limit=1&addressdetails=1';
  
      fetch(url)
        .then(function (response) {
          return response.json();
        })
        .then(function (data) {
          if (data.length > 0) {
            var result = data[0];
            var lat = result.lat;
            var lon = result.lon;
  
            // Update the map view to the search result
            map.setView([lat, lon], 16);
  
  
            // Add a marker at the search result
            L.marker([lat, lon]).addTo(map);
          }
        })
        .catch(function (error) {
          console.log('Error:', error);
        });
    }
  
    // Add event listener for the search input
    searchInput.addEventListener('change', function () {
        removeAllMarkers();
        performSearch();
    });
  
    function change() {
     if(btn.hasClass('active')){
      button.removeClass('active')
     } else {
      btn.toggleClass('active')
     }
    }
}

function cambioPagina(URL){
    const ID = localStorage.getItem("token")
    if (ID!=null){
        window.location.href = URL
    }else{
        swal({
            title: "Non hai ancora eseguito il l'accesso",
            text: "Scegli una delle seguenti azioni:",
            icon: "info",
            buttons: {
              opzione1: {
                text: "Accedi",
                value: "accedi",
              },
              opzione2: {
                text: "Registrati",
                value: "registrati",
              },
              cancel: "Chiudi"
            },
          })
          .then((value) => {
            switch (value) {
              case "accedi":
                // Logica per l'opzione 1
                window.location.href = "Login.html";
                break;
              case "registrati":
                // Logica per l'opzione 2
                window.location.href = "Registrazione.html";
                break;
              default:
                // Azioni da eseguire se viene premuto "Annulla" o viene chiusa la finestra
                break;
            }
          });
    }
}