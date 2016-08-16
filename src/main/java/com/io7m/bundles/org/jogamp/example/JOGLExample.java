/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.bundles.org.jogamp.example;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLProfile;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * JOGL example.
 */

@Component(immediate = true)
public final class JOGLExample
{
  private GLWindow window;
  private GLContext context;

  public JOGLExample()
  {

  }

  private void log(final String message)
  {
    System.out.printf(
      "[%s][%s]: %s\n",
      JOGLExample.class.getName(),
      Thread.currentThread().getName(),
      message);
  }

  @Activate
  private void activate()
  {
    this.log("Fetching GL profile...");

    final GLProfile profile = GLProfile.get(GLProfile.GL3);
    this.log("Got GL profile: " + profile);

    final GLCapabilities caps = new GLCapabilities(profile);
    this.log("Got GL capabilities: " + caps);

    this.log("Opening window...");
    this.window = GLWindow.create(caps);
    this.window.setTitle("JOGL OSGi");
    this.window.setSize(640, 480);
    this.window.setVisible(true);

    this.log("Window opened");
    this.context = this.window.getContext();
    this.context.makeCurrent();

    this.log("Clearing window...");
    final GL3 gl3 = this.context.getGL().getGL3();
    gl3.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
    gl3.glClear(GL.GL_COLOR_BUFFER_BIT);
    this.context.release();

    this.window.display();
    this.log("Window cleared");
  }

  @Deactivate
  private void deactivate()
  {
    this.log("Destroying window");
    this.window.setVisible(false);
    this.window.destroy();
  }
}
