function setupToggle(innerElement, hideTitle, showTitle) {

  document.write(" <span id='" + innerElement + "_hide_link'>hide</span>");
  document.write(" <span id='" + innerElement + "_show_link'>show</span>");

  var hide_link = document.getElementById(innerElement + "_hide_link");
  hide_link.innerHTML = hideTitle;
  hide_link.style.fontSize = '7pt';
  hide_link.style.cursor = 'pointer';

  var show_link = document.getElementById(innerElement + "_show_link");
  show_link.innerHTML = showTitle;
  show_link.style.fontSize = '7pt';
  show_link.style.cursor = 'pointer';

  hide_link.onclick = function() {
    show_link.style.display = '';
    hide_link.style.display = 'none';

    var content = document.getElementById(innerElement);
    content.style.display = 'none';
  }

  show_link.onclick = function() {
    show_link.style.display = 'none';
    hide_link.style.display = '';

    var content = document.getElementById(innerElement);
    content.style.display = '';
  }
}

function setToggled(innerElement, shown) {
  var content = document.getElementById(innerElement);
  var show_link = document.getElementById(innerElement + "_show_link");
  var hide_link = document.getElementById(innerElement + "_hide_link");
  if (shown) {
    show_link.style.display = 'none';
    hide_link.style.display = '';
    content.style.display = '';
  }
  else {
    show_link.style.display = '';
    hide_link.style.display = 'none';
    content.style.display = 'none';
  }

}
