$(document).ready(function () {

    $("#input-ficons-4").fileinput({
        uploadUrl: "/file-upload-batch/2",
        previewFileIcon: '<i class="fa fa-file"></i>',
        showPreview: true,
        browseOnZoneClick: true,
        maxFileCount: 1,
        allowedPreviewTypes: ['image'], // disable preview of standard types
        allowedPreviewMimeTypes: ['image/jpeg', 'text/javascript'], // allow content to be shown only for certain mime types 
        previewFileIconSettings: {
            'docx': '<i class="fa fa-file-word-o text-primary"></i>',
            'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
            'pptx': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            'zip': '<i class="fa fa-file-archive-o text-muted"></i>'
        }
    });

    $('#input-ficons-4').on('fileloaded', function (event, file, previewId, index, reader) {
        //debugger;
        console.log("fileloaded");
    });

});



// Note: This example requires that you consent to location sharing when
// prompted by your browser. If you see the error "The Geolocation service
// failed.", it means you probably did not give permission for the browser to
// locate you.

function initMap() {

    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 12,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        disableDefaultUI: true
    });
    var infoWindow = new google.maps.InfoWindow({map: map});

    // Try HTML5 geolocation.
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            infoWindow.setPosition(pos);
            infoWindow.setContent('Location found.');
            map.setCenter(pos);
        }, function () {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
            'Error: The Geolocation service failed.' :
            'Error: Your browser doesn\'t support geolocation.');
}