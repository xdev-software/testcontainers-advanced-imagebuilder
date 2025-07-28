/**
 * This package was forked from the OpenJDK (as of 21.0.7) to resolve a crash/infinite loop when encountering
 * <a href="https://github.com/xdev-software/testcontainers-advanced-imagebuilder/issues/155">
 * recursive NTFS junctions on Windows
 * </a>.
 *
 * @see <a href="https://en.wikipedia.org/wiki/NTFS_links#Junction_points">NTFS junction</a>
 */
package software.xdev.testcontainers.imagebuilder.transfer.java.nio.file.winntfs;
