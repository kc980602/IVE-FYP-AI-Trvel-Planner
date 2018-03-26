$(document).ready(function() {
  $('select').material_select();
  $('#btnBW').prop('disabled', true)
  $('#step1').addClass('show').show()
  $('#step2').removeClass('show').hide()
});


$('#btnBW').click(function() {
  var step = $('#content-wizard').data('step')
  if (step) {
    $('#content-wizard').data('step', --step)
    $(this).prop('disabled', true)
    $('#step1').addClass('show')
    $('#step2').removeClass('show')
    setTimeout(() => {
      $('#step1').show()
      $('#step2').hide()
    }, 150)
    $('#btnFW').html('Forward <i class="material-icons right">keyboard_arrow_right</i>')
    $('#top-wizard .determinate').css({width: '0%'})
  }
})

$('#btnFW').click(function() {
  var step = $('#content-wizard').data('step')
  var gender      = $('input[name=gender]:checked').val();
  var ageGroup    = $('#ageGroup').val();
  var income      = $('#income').val();
  var expenditure = $('#expenditure').val();
  var destination = $('#destination').val();
  if (step == 1 || step == undefined) {
    if(gender==undefined || ageGroup==null ||
       income==null || expenditure==null )
      {
        alert("Please fill all required field");
              return false;
    } else {
      $('#btnBW').prop('disabled', false)
      $('#content-wizard').data('step', 2)
      $('#top-wizard .determinate').css({width: '50%'})
      $('#step1').removeClass('show')
      $('#step2').addClass('show')
      setTimeout(() => {
        $('#step1').hide()
        $('#step2').show()
      }, 150)
      $(this).html('Submit <i class="material-icons right">send</i>')
    }
  } else if (step == 2) {
    if (gender==undefined || ageGroup==null ||
       income==null || expenditure==null || destination==null)
       {
         alert("Please fill all required field");
               return false;
    } else {
      $('#top-wizard .determinate').css({width: '100%'})
      $('#middle-wizard').html(`<h5 class="light">Thank you for your response</h5>`)
      $('#bottom-wizard').remove()
      alert('POST -> API')
    }
  }
});

$('input[type=range]').change(function() {
  if($(this).val() == 0){
    $(this).parent().children('.type-description').html('<span style=\'color:#DD0000\'>Strongly Disagree</span>');
  } else if ($(this).val() == 1){
    $(this).parent().children('.type-description').html('<span style=\'color:#AA0000\'>Disagree</span>');
  } else if ($(this).val() == 2){
    $(this).parent().children('.type-description').html('<span style=\'color:#000000\'>Neutral</span>');
  } else if ($(this).val() == 3){
    $(this).parent().children('.type-description').html('<span style=\'color:#009900\'>Slightly Agree</span>');
  } else if ($(this).val() == 4){
    $(this).parent().children('.type-description').html('<span style=\'color:#00BB00\'>Agree</span>');
  } else if ($(this).val() == 5){
    $(this).parent().children('.type-description').html('<span style=\'color:#00DD00\'>Strongly Agree</span>');
  }
});

function valueToText(value){
  if (value == 0) {
    return "Strongly Disagree";
  } else if (value == 1) {
    return "Disagree";
  } else if (value == 2) {
    return "Neutral";
  } else if (value == 3) {
    return "Slightly Agree";
  } else if (value == 4) {
    return "Agree";
  } else if (value == 5) {
    return "Strongly Agree";
  }
}

$('#btnGenerate').click(function() {
  alert("Gender\t:" + $('input[name=gender]:checked').val() + "\n" +
        "Age Group\t:" + $('#ageGroup').val() + "\n" +
        "Income\t:" + $('#income').val() + "\n" +
        "Occupation\t:" + $('#expenditure').val() + "\n" +
        "\n" +
        "Family Vacationer\t:" + valueToText($('#family_holiday_maker').val()) + "\n" +
        "Foodie\t:" + valueToText($('#foodie').val()) + "\n" +
        "Backpacker\t:" + valueToText($('#backpacker').val()) + "\n" +
        "History Buff\t:" + valueToText($('#history_buff').val()) + "\n" +
        "Nightlife Seeker\t:" + valueToText($('#nightlife_seeker').val()) + "\n" +
        "Eco-tourist\t:" + valueToText($('#eco_tourist').val()) + "\n" +
        "Trendsetter\t:" + valueToText($('#trendsetter').val()) + "\n" +
        "Nature Lover\t:" + valueToText($('#nature_lover').val()) + "\n" +
        "Thrifty Traveler\t:" + valueToText($('#thrifty_traveller').val()) + "\n" +
        "Art and Architecture Lover\t:" + valueToText($('#art_and_architecture_lover').val()) + "\n" +
        "Urban Explorer\t:" + valueToText($('#urban_explorer').val()) + "\n" +
        "Thrill Seeker\t:" + valueToText($('#thrill_seeker').val()) + "\n" +
        "Beach Goer\t:" + valueToText($('#beach_goer').val()) + "\n" +
        "60+ Traveler\t:" + valueToText($('#sixtyPlus_traveller').val()) + "\n" +
        "Like a Local\t:" + valueToText($('#like_a_local').val()) + "\n" +
        "Luxury Traveller\t:" + valueToText($('#luxury_traveller').val()) + "\n" +
        "Vegetarian\t:" + valueToText($('#vegetarian').val()) + "\n" +
        "Shopping Fanatic\t:" + valueToText($('#shopping_fanatic').val()) + "\n" +
        "Peace and Quiet See\t:" + valueToText($('#peace_and_quiet_seeker').val()) + "\n"
  );
});
