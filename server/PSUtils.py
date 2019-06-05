from win32com.client import Dispatch, GetActiveObject, GetObject

def hex_to_rgb(hex_string):
    return tuple(int(hex_string[i:i+2], 16) for i in (0,2,4))

def get_instance():
    try:
        app = GetActiveObject("Photoshop.Application")
        return app
    except:
        return None

def set_fg_color(hex_string):
    app = get_instance()

    if app:
        new_color = Dispatch("Photoshop.SolidColor")
        new_color.RGB.Red, new_color.RGB.Green, new_color.RGB.Blue = hex_to_rgb(hex_string) 
        app.ForegroundColor = new_color

def set_tool(tool):
    app = get_instance()
    if app:
        desc9 = Dispatch("Photoshop.ActionDescriptor")
        ref7 = Dispatch("Photoshop.ActionReference")
        #print app.StringIDToTypeID(tool)
        ref7.PutClass(app.StringIDToTypeID(tool))
        desc9.PutReference(app.CharIDToTypeID('null'), ref7)
        app.ExecuteAction(app.CharIDToTypeID('slct'), desc9, 3)

#if __name__ == "__main__":
 #   set_tool("magicStampTool")