<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version = "1.0"
                xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Students</h2>
                <table border ="1">
                    <tr bgcolor = "#aba3a2">
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Student age</th>
                        <th>Subject name</th>
                    </tr>
                    <xsl:for-each select="students/student">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="first-name"/></td>
                            <td><xsl:value-of select="last-name"/></td>
                            <td><xsl:value-of select="age"/></td>
                        </tr>
                        <xsl:for-each select="subjects/subject">
                            <tr>
                                <td></td><td></td><td></td><td></td>
                                <td><xsl:value-of select="name"/></td>
                            </tr>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>