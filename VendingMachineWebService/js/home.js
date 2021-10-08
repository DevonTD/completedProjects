$(document).ready(function () {
  loadVendingItems();
  addMoney();
  vendItem();
  makeChange();
});

function loadVendingItems(){
  clearLeftDiv(); // Refresh
  var contentDivs = $('#leftDiv');


  $.ajax({
    type: 'GET',
    url: 'http://vending.us-east-1.elasticbeanstalk.com/items',
    success: function(vendingItem){
      $.each(vendingItem, function(index, item){
        var name = item.name;
        var price = item.price;
        var quantity = item.quantity;
        var itemId = item.id;

        var newItem = '<div class="card border-dark m-3" onclick="selected(' + itemId +');">';
            newItem += '<div class="card-header text-center" style="font-weight:bold;"><span>' + itemId + '</span>' + name + '</div>';
            newItem += '<div class="card-body text-dark">';
            newItem += '<h5 class="card-title text-center">$' + price + '</h5>';
            newItem += '<p class="card-text text-center">Quantity Left: ' + quantity +'</p>';
            newItem += '</div>';
            newItem += '</div>';

            contentDivs.append(newItem); // Creates a new row of info per object loop
      });

      $('.card').hover(
          // in callback
          function () {
              $(this).css("background-color", "DarkTurquoise");
          },
          // out callback
          function () {
              $(this).css("background-color", "");
          }
      );
    },
    error: function(){
      $('#errorMessage')
      .append($('<li>')
      .attr({class: 'list-group-item list-group-item-danger'})
      .text('Error calling web service. Please try again later'));
    }
  });
} // Loads vending machine items

function selected(id){
    $('#itemSelect').val(id); // Parameter is used for an objects Id
}

function addMoney(){
  let startingValue = 0;
  let dollar = 1;
  let quarter = .25;
  let dime =  .1;
  let nickel = .05;

  $('#sumOfMoney').val(startingValue);

  $('#addDollar').click(function(event){
    let currentValueD = parseFloat(document.getElementById("sumOfMoney").value);
    let resultD = currentValueD + dollar;
    $('#sumOfMoney').val(resultD.toFixed(2));
    // Add dollar value on click into sumOfMoney
  });
  $('#addQuarter').click(function(event){
    let currentValueQ = parseFloat(document.getElementById("sumOfMoney").value);
    let resultQ = currentValueQ + quarter;
    $('#sumOfMoney').val(resultQ.toFixed(2));
    // Add quarter value on click into sumOfMoney
  });
  $('#addDime').click(function(event){
    let currentValueDi = parseFloat(document.getElementById("sumOfMoney").value);
    let resultDi = currentValueDi + dime;
    $('#sumOfMoney').val(resultDi.toFixed(2));
    // Add dime value on click into sumOfMoney
  });
  $('#addNickel').click(function(event){
    let currentValueN = parseFloat(document.getElementById("sumOfMoney").value);
    let resultN = currentValueN + nickel;
    $('#sumOfMoney').val(resultN.toFixed(2));
    // Add nickel value on click into sumOfMoney
  });
}

function vendItem(){
  $('#purchaseButton').click(function(event){
    clearChangeField(); // clears input field
    let money = parseFloat(document.getElementById("sumOfMoney").value);
    let itemId = parseInt(document.getElementById("itemSelect").value);

    $.ajax({
      type: 'POST',
      url: 'http://vending.us-east-1.elasticbeanstalk.com/' + money + '/item/' + itemId,
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      dataType: 'json',
      success: function(data, textStatus, jqXHR){
        let q = parseInt(data.quarters); // Converts the JSON data into integers the variable names are for each respective coin type
        let d = parseInt(data.dimes);
        let n = parseInt(data.nickels);
        let p = parseInt(data.pennies);

          $('#errorMessages').val("Thank you!!");
          clearMoneyField();
          loadVendingItems();
            $('#changeField').val(q + " Quarter(s) " + d + " Dime(s) " + n + " Nickel(s) " + p + " Pennie(s)");
      },
      error: function(jqXHR, textStatus, errorThrown){
          if(jqXHR.status == 422){
              $('#errorMessages').val(jqXHR.responseJSON.message); // recieves http error status and extracts the JSON response
          }else{
            $('#errorMessages').val("Please select an Item");
          } // without this if statement an HTTP 400 is thrown that cannot be converted into JSON. This stops that from happening
      }
    });
  })
} // purchase an item

function makeChange(){
  $('#changeButton').click(function(event){
    if($('#sumOfMoney').val() == 0){
      $('#changeField').val("")
    }else {
      var change = $('#sumOfMoney').val(); // Gets amount from input field
      var subtractTotal = change * 100; // multiplys amount by 100 for use in division

      var quarterReturn = Math.floor(subtractTotal/25); // returns the amount of quarters based on the quarter value divided by the amount due in change
      subtractTotal = subtractTotal % 25; // value is updated with what remainds to be divided

      var dimeReturn = Math.floor(subtractTotal/10);
      subtractTotal = subtractTotal % 10;

      var nickelReturn = Math.floor(subtractTotal/5);
      subtractTotal = subtractTotal % 5;

      $('#changeField').val(quarterReturn + " Quarter(s) " + dimeReturn + " Dime(s) " + nickelReturn + " Nickel(s)");
      clearFields();
    }
  })
} // make change



function clearLeftDiv(){
  $('#leftDiv').empty();
}
function clearMoneyField(){
  $('#sumOfMoney').val(0);
}
function clearChangeField(){
  $("#changeField").val("");
}
function clearFields(){
  $('#sumOfMoney').val(0);
  $('#errorMessages').val("");
  $('#itemSelect').val("");
}
