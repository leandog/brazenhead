class Navigation
  def initialize(driver)
    @driver = driver
  end

  def view_list
    views
  end

  def controls_screen
    views
    controls
    light_theme
  end

  def lists_screen
    views
    lists
  end

  def custom_lists_screen
    lists_screen
    custom_list_items
  end

  private
  def press_list_item(locator)
    press_by_text(locator[:text]) if locator[:text]
    press_by_index(locator[:index]) if locator[:index]
  end

  def press_by_text(text)
    @driver.click_on_text text
  end

  def press_by_index(index)
    @driver.press_list_item_by_index index, :target => 'Brazenhead' 
  end

  def views
    press_list_item(:index => 11)
  end

  def controls
    press_list_item(:text => 'Controls')
  end

  def light_theme
    press_list_item(:text => 'Light Theme')
  end

  def lists
    press_list_item(:index => 21)
  end

  def custom_list_items
    press_list_item(:index => 18)
  end

end
