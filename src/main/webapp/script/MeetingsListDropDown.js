var f =['Sandwish', 'Hamburguer', 'Tacos']
var string = '';

jQuery.ajax({
    url: "http:
    type: "POST",
    data : { 'data1':'something'
        },
    success: function (item){
if (item) {
  for (var x = 0; x < item.length; x++) {
   var filler = '<option value=' + item[x] + '">' + item[x] + '</option>';
    string +=filler;
  }
  $("#meetings-list").html(string);
}
}
});
