function performAjaxSubmit(pageContext) {

    var photoFile = document.getElementById("photo").files[0];


    var formdata = new FormData();


    formdata.append("sampleFile", photoFile);


    var xhr = new XMLHttpRequest();


    xhr.open("POST", pageContext + "/enterprises/uploadFile", true);


    xhr.send(formdata);


    xhr.onload = function (e) {


        if (this.status == 200) {

            alert("Photo Successfully uploaded")

        }

    };


}


function handleFileSelect(evt) {


    var files = evt.target.files; // FileList object

    // Loop through the FileList and render image files as thumbnails.
    for (var i = 0, f; f = files[i]; i++) {

        // Only process image files.
        if (!f.type.match('image.*')) {
            alert("not image");
            continue;
        }


        var reader = new FileReader();

// Closure to capture the file information.
        reader.onload = (function (theFile) {
            return function (e) {
                // Render thumbnail.
                var span = document.createElement('span');
                span.innerHTML = ['<img class="thumb" src="', e.target.result,
                    '" title="', escape(theFile.name), '"/>'].join('');
                document.getElementById('list').insertBefore(span, null);

            };
        })(f);

// Read in the image file as a data URL.

        reader.readAsDataURL(f);

    }

}


document.getElementById('photo').addEventListener('change', handleFileSelect, false);
