action takePicture : TakePicture {
    in scene;

    action focus : Focus {
        in scene = takePicture::scene;
        out image;
    }

    first focus
        if focus.image.isFocused then shoot;

    flow focus.image to shoot.image;

    action shoot : Shoot {
        in image;
        out picture;
    }

    out picture = shoot.picture;
}
