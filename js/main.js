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
  if (step == 1 || step == undefined) {
    var gender      = $('input[name=gender]:checked').val();
    var ageGroup    = $('#ageGroup').val();
    var income      = $('#income').val();
    var expenditure = $('#expenditure').val();
    if(gender==undefined || ageGroup==null ||
       income==null || expenditure==null )
      {
        alert("Please Fill All Required Field");
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
    $('#top-wizard .determinate').css({width: '100%'})
    $('#middle-wizard').html(`<h5 class="light">Thank you for your response</h5>`)
    $('#bottom-wizard').remove()
    alert('POST -> API')
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
