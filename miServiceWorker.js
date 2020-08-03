console.log("Yo soy el serviceWorker");
// var eventoNotifi = self.createEvent('Event');
// eventoNotifi.initEvent('eventoNotifi', true, true);
//

const staticCacheName = 'site-static';
const assets = [
  'index.html',
  'Semantic-UI/semantic.min.css',
  'jquery.min.js',
  'Semantic-UI/semantic.min.js',
  'miScript.js'
];


// Instalar el service worker
// install event
self.addEventListener('install', evt => {
  //console.log('service worker installed');
  evt.waitUntil(
    caches.open(staticCacheName).then((cache) => {
      console.log('%cCargando assets al cache',"color: #F95;");
      cache.addAll(assets);
    }).catch((error)=>{
      console.log("Ocurrio un error al cargar al cache ",error);
    })
  );
});


self.addEventListener('activate',(evet)=>{
  console.log("El serviceWorker ha sido activado");
});

// fetch event
self.addEventListener('fetch', evt => {
  evt.respondWith(
    caches.match(evt.request).then(cacheRes => {
      return cacheRes || fetch(evt.request);
    })
  );

  console.log(evt.request.url);
  console.log(evt.request.url.includes('getAllPersonas'));
  if (evt.request.url.includes('getAllPersonas')) {
    self.registration.showNotification("Actualizacion", {
      body: 'Se actualizo la tabla.',
      icon: 'img/crud_create_read_update_delete-512.png'
      });
  }
});



setTimeout(() => {
  self.registration.showNotification("Hola", {
    body: 'Soy goku.',
    icon: 'img/crud_create_read_update_delete-512.png'
    });
}, 5000);
