/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function() {
    
    if ($('#cartMsg').val() !== null) {
        $('.alert').toggleClass('in out');
    } 
    
    $("#pwBtn").click(function() {
        $(".pwCon").toggle("slide");
    });
    
});


function validate() {
    $pw = $('#pw').val();
    $cfmPw = $('#cfmPw').val();
    $postal = $('#postal').val();
    $mobile = $('#mobile').val();
    
    if($pw !== $cfmPw)  {
        $('#pwErr').html("Passwords do not match.");
        return false;
    }
    else if (!$mobile.match('^[0-9]{8}$'))  {
        $('#pwErr').html("");
        $('#posErr').html("");
        $('#mobErr').html("Mobile number has to be exactly 8 digits.");
        return false;
    }
    else if (!$postal.match('^[0-9]{6}$')) {
        $('#pwErr').html("");
        $('#posErr').html("Postal Code has to be exactly 6 digits.");
        return false;
    }

    return true;
}

function isLoggedIn() {
    if (!$('#userLoggedIn').val()) {
        $("#cartErr").html("You must be signed in to add to cart!");
        return false;
    }
    return true;
}