

    $('.md-select').on('click', function(){
    $(this).toggleClass('active')
})

$('.md-select ul li').on('click', function() {
    var v = $(this).text();
    $('.md-select ul li').not($(this)).removeClass('active');
    $(this).addClass('active');
    $('.md-select label button').text(v)
})
// This is "probably" IE9 compatible but will need some fallbacks for IE8
// - (event listeners, forEach loop)

// wait for the entire page to finish loading
window.addEventListener('load', function() {

    // setTimeout to simulate the delay from a real page load
    setTimeout(lazyLoad, 1000);

});

function lazyLoad() {
    var card_images = document.querySelectorAll('.card-image');

    // loop over each card image
    card_images.forEach(function(card_image) {
        var image_url = card_image.getAttribute('data-image-full');
        var content_image = card_image.querySelector('img');

        // change the src of the content image to load the new high res photo
        content_image.src = image_url;

        // listen for load event when the new photo is finished loading
        content_image.addEventListener('load', function() {
            // swap out the visible background image with the new fully downloaded photo
            card_image.style.backgroundImage = 'url(' + image_url + ')';
            // add a class to remove the blur filter to smoothly transition the image change
            card_image.className = card_image.className + ' is-loaded';
        });

    });

}

    $('#login').submit(function(e) {
        e.preventDefault();
        var username = $('#username').val();
        var password = $('#password').val();

       var submitted=true;


        if (username.length < 1) {
            // $('#first_name').after('<span class="error">This field is required</span>');
            alert("Input username")
        }
        if(username.charAt(0)== '0' ||'1' || '2' || '3' || '4' || '4' || '5' || '6' || '7' ||'8'||'9'){
            alert("Username cannot start with a number")
            console.log(username)
            console.log(username.charAt(0))

        }
        $("#login")[0].reset();

        $("select.dropdownlocation").change(function(){
            var selectedCountry = $(this).children("option:selected").val();
            $("#location").html(selectedCountry);
        });
        $("select.dropdownanimals").change(function(){
            var selectedAnimal = $(this).children("option:selected").val();
            $("#animal").html(selectedAnimal);
        });
        $("select.dropdownrangers").change(function(){
            var selectedRanger = $(this).children("option:selected").val();
            $("#ranger").html(selectedRanger);
        });
        //
        // if (email.length < 1) {
        //     $('#email').after('<span class="error">This field is required</span>');
        // } else {
        //     var regEx = /^[A-Z0-9][A-Z0-9._%+-]{0,63}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/;
        //     var validEmail = regEx.test(email);
        //     if (!validEmail) {
        //         $('#email').after('<span class="error">Enter a valid email</span>');
        //     }
        // }
        // if (password.length < 8) {
        //     $('#password').after('<span class="error">Password must be at least 8 characters long</span>');
        // }
    });


