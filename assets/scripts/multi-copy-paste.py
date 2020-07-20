def get_replacement(x):
	return '\\"'

editor.replace('style="background: #ffffff; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;"', 'class="code-block"')
editor.replace('style="margin: 0; line-height: 125%"', 'class="pre-code-block"')
editor.replace('style="color: #0000DD; font-weight: bold"', 'class="code-line-number"')
editor.replace('style="color: #333333"', 'class="code-default-color"')
editor.replace('style="color: #008800; font-weight: bold"', 'class="code-keyword"')
editor.replace('style="color: #BB0066; font-weight: bold"', 'class="code-class-name"')
editor.replace('style="color: #333399; font-weight: bold"', 'class="code-primitive"')
editor.replace('style="color: #0e84b5; font-weight: bold"', 'class="code-static-import"')
editor.replace('style="color: #0000CC"', 'class="code-method"')
editor.replace('style="color: #0000DD; font-weight: bold"', 'class="code-literal"')
editor.replace('style="background-color: #fff0f0"', 'class="code-string"')
editor.replace('style="color: #888888"', 'class="code-comment"')

editor.replace('<!-- HTML generated using hilite.me -->', '')

# Needs an external method to work (no idea why)
editor.replace('"', get_replacement)

editor.selectAll()
editor.linesJoin()

editor.cut()