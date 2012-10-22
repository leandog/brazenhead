class Navigation
  def initialize(driver)
    @driver = driver
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
  def views
    @driver.click_on_text 'Views'
  end

  def controls
    @driver.click_on_text 'Controls'
  end

  def light_theme
    @driver.click_on_text 'Light Theme'
  end

  def lists
    @driver.click_on_text '^Lists$'
  end

  def custom_list_items
    @driver.click_on_text "18\. Custom items"
  end

end
