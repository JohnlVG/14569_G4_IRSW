Feature: Cotizar productos
  # Como comprador
  # Quiero poder cotizar productos
  # Para enviar al vendedor

  Scenario: Validar que se pueda ingresar los productos correctamente a la lista
    Given Quiero cotizar productos
    When Doy click en el bot1 "<boton1>"
    Then Se debe validar que los productos se ingresen correctamente en la lista

    Examples:
      | boton1 |
      | COTIZAR |

